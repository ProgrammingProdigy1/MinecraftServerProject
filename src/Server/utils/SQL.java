package Server.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQL {

	private static Connection connection;

	public static Connection getConnection() {
		return connection;
	}

	public static synchronized void openConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/minecraft_server", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void closeConnection() {
		try {
			if (!(connection.isClosed()))
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * tells whether a player's data is stored in a given table.
	 * 
	 * @param player the player
	 * @param table  the table
	 * @return true if the player's data is stored in the table, false otherwise.
	 */
	private static synchronized boolean tableContains(UUID player, String table) {
		boolean result = false;
		try {
			PreparedStatement sql = connection
					.prepareStatement(String.format("SELECT player FROM %s WHERE player = ?;", table));
			sql.setString(1, player.toString());
			ResultSet rs = sql.executeQuery();
			result = rs.next();
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	public static synchronized <T extends Object> boolean tableContains(T data, String table, String column) {
		boolean result = false;
		try {
			PreparedStatement sql = connection
					.prepareStatement(String.format("SELECT %s FROM %s WHERE %s = ?;", column, table, column));
			sql.setObject(1, data);
			ResultSet rs = sql.executeQuery();
			result = rs.next();
			sql.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return result;
		}
	}

	/**
	 * retrieves a generic info from a container in a given table.
	 * 
	 * @param <T>
	 * @param table  the table in which the information is stored.
	 * @param player the player about who the info row.
	 * @param column the specific column to be retrieved from the row.
	 * @param type   the type of the data that shall be retrieved.
	 * @return the data from the table, as a generic type specified as an argument.
	 */
	public static synchronized <T extends Object> Object queryInfo(String table, UUID player, String column,
			Class<T> type) {
		Object obj = null;
		openConnection();
		try {
			if (tableContains(player, table)) {
				PreparedStatement sql = connection
						.prepareStatement(String.format("SELECT %s FROM %s WHERE player=?;", column, table));
				sql.setString(1, player.toString());
				ResultSet rs = sql.executeQuery();
				rs.next();
				obj = rs.getObject(column, type);
				sql.close();
				closeConnection();
				return obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
			return obj;
		}
	}

	public static synchronized <T extends Object, K extends Object> T queryInfo(String table, String primaryField,
			K primary, String column, Class<T> type) {
		try {
			openConnection();
			DatabaseMetaData data = connection.getMetaData();
			ResultSet keys = data.getPrimaryKeys(null, "root", table);
			if (!keys.next())
				return null;
			if (!(keys.getString("COLUMN_NAME").equals(primaryField)))
				return null;
			String statement = "SELECT %s FROM %s WHERE %s=?";
			PreparedStatement query = connection
					.prepareStatement(String.format(statement, column, table, primaryField));
			query.setObject(1, primary);
			ResultSet res = query.executeQuery();
			query.close();
			closeConnection();
			return res.getObject(column, type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized <T extends Object> List<Object> queryInfo(String table, String column, T... conditions) {
		try {
			openConnection();
			if (conditions == null || conditions.length % 2 != 0 || !(conditions[0] instanceof String))
				return null;
			String statement = "SELECT %s FROM %s WHERE %s=?";
			statement = String.format(statement, column, table, (String) conditions[0]);
			String addition = " AND %s=?";
			int times = conditions.length;
			for (int i = 2; i < times; i += 2) {
				statement += addition;
				statement = String.format(statement, (String) conditions[i]);
			}
			PreparedStatement sql = connection.prepareStatement(statement);
			times /= 2;
			int obj = 1;
			for (int j = 1; j <= times; j++) {
				sql.setObject(j, conditions[obj]);
				obj += 2;
			}
			ResultSet res = sql.executeQuery();
			List<Object> lst = new ArrayList<Object>();
			while (res.next()) {
				lst.add(res.getObject(column));
			}
			res.close();
			sql.close();
			closeConnection();
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized <T extends Object> List<T> queryRegular(String table, String column, String expColumn,
			String exp, Class<T> type) {
		try {
			openConnection();
			String statement = "SELECT %s FROM %s WHERE %s REGEXP ?";
			PreparedStatement query = connection.prepareStatement(String.format(statement, column, table, expColumn));
			query.setString(1, exp);
			ResultSet res = query.executeQuery();
			List<T> lst = new ArrayList<T>();
			while (res.next()) {
				lst.add(res.getObject(column, type));
			}
			res.close();
			query.close();
			closeConnection();
			return lst;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * inserts a single info row into a given mySql table, which has to do with a
	 * player specified as a UUID.
	 * 
	 * @param <T>
	 * @param table      the table to insert row into
	 * @param player     a player the data has to do with.
	 * @param mappedData the new row's partial or full data. the data shall be
	 *                   inserted as a variable argument array, which consist of the
	 *                   structure:"column1",data1,"column2",data2...
	 */
	public static synchronized <T extends Object> void insertInfoRow(String table, UUID player, T... mappedData) {
		openConnection();
		try {
			if (!(tableContains(player, table))) {
				String statement = "INSERT INTO %s(player) VALUES(?);";
				PreparedStatement insertion = connection.prepareStatement(String.format(statement, table));
				insertion.setString(1, player.toString());
				insertion.execute();
				insertion.close();
				if (mappedData == null || mappedData.length % 2 != 0) {
					closeConnection();
					return;
				}
				PreparedStatement sql = null;
				int times = mappedData.length;
				statement = "UPDATE %s SET %s=? WHERE player=?;";
				for (int i = 0; i < times; i += 2) {
					sql = connection.prepareStatement(String.format(statement, table, mappedData[i]));
					sql.setObject(1, mappedData[i + 1]);
					sql.setString(2, player.toString());
					sql.executeUpdate();
				}
				if (sql != null)
					sql.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public static synchronized <T extends Object> void insertInfoRow(String table, T... mappedData) {
		openConnection();
		try {
			if (mappedData == null || mappedData.length == 0 || mappedData.length % 2 != 0
					|| !(mappedData[0] instanceof String)) {
				closeConnection();
				return;
			}
			DatabaseMetaData data = connection.getMetaData();
			ResultSet keys = data.getPrimaryKeys(null, "root", table);
			if (!keys.next())
				return;
			if (!(keys.getString("COLUMN_NAME").equals((String) mappedData[0])))
				return;
			String statement = String.format("INSERT INTO %s(%s) VALUES(?);", table, (String) mappedData[0]);
			PreparedStatement insertion = connection.prepareStatement(statement);
			insertion.setObject(1, mappedData[1]);
			insertion.execute();
			insertion.close();
			String statement2 = "UPDATE %s SET %s=? WHERE %s=?", save = statement2;
			PreparedStatement sql = null;
			int times = mappedData.length;
			for (int i = 2; i < times; i += 2) {
				statement2 = String.format(save, table, mappedData[i], mappedData[0]);
				sql = connection.prepareStatement(statement2);
				sql.setObject(1, mappedData[i + 1]);
				sql.setObject(2, mappedData[1]);
				sql.executeUpdate();
			}
			if (sql != null)
				sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public static synchronized <T extends Object> void insertInfoRowBeta(String table, T... mappedData) {
		try {
			openConnection();
			if (mappedData == null)
				return;
			int len = mappedData.length;
			if (len == 0 || len % 2 != 0)
				return;
			String base = "INSERT INTO %s", fields = "(", values = " VALUES(?";
			if (mappedData[0] instanceof String)
				fields += mappedData[0];
			else
				return;
			len /= 2;
			for (int i = 1; i < len; i++) {
				if (mappedData[i] instanceof String)
					fields += "," + mappedData[i];
				else
					return;
			}
			fields += ")";
			for (int i = 1; i < len; i++)
				values += ",?";
			values += ")";
			PreparedStatement sql = connection.prepareStatement(String.format(base + fields + values, table));
			for(int i = 0; i < len; i++)
				sql.setObject(i+1, mappedData[i+len]);
			sql.execute();
			sql.close();
			closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * updates a single data container.
	 * 
	 * @param <T>
	 * @param table  the mySql table to update data from.
	 * @param player is the player about whom the data.
	 * @param column the specific category we desire to update
	 * @param data   is the new data to replace with the former data.
	 */
	public static synchronized <T extends Object> void updateInfo(String table, UUID player, String column, T data) {
		openConnection();
		try {
			if (tableContains(player, table)) {
				PreparedStatement sql = connection
						.prepareStatement(String.format("UPDATE %s SET %s=? WHERE player=?;", table, column));
				sql.setObject(1, data);
				sql.setString(2, player.toString());
				sql.executeUpdate();
				sql.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	/**
	 * inserts multiple rows into a given table.
	 * 
	 * @param <T>
	 * @param table      a table to insert data into.
	 * @param mappedData a generic variable argument array which represents the data
	 *                   rows to be inserted into a table. the array should consist
	 *                   of the structure:"player",player_uuid,"column1",data1...
	 * 
	 */
	public static synchronized <T extends Object> void insertInfoRows(String table, T... mappedData) {
		openConnection();
		try {
			if (mappedData == null || mappedData.length == 0 || !(mappedData[0] instanceof String))
				return;
			UUID player = null;
			String statement = String.format("INSERT INTO %s(player) VALUES(?)", table),
					update = "UPDATE %s SET %s=? WHERE player=?;";
			int len = mappedData.length;
			PreparedStatement insertion = connection.prepareStatement(statement), complete = null;

			A: for (int i = 0; i < len; i += 2) {
				if (len - i == 1)
					break A;
				Object obj = mappedData[i];
				if (obj instanceof String && ((String) obj).toLowerCase().equals("player")) {
					player = (mappedData[i + 1] instanceof UUID) ? ((UUID) mappedData[i + 1]) : null;
					if (tableContains(player, table))
						continue A;
					insertion.setString(1, player.toString());
					insertion.execute();
					insertion = connection.prepareStatement(statement);
				} else if (obj instanceof String) {
					if (player == null || tableContains(player, table))
						continue A;
					complete = connection.prepareStatement(String.format(update, table, mappedData[i]));
					complete.setObject(1, mappedData[i + 1]);
					complete.setString(2, player.toString());
					complete.executeUpdate();
				} else
					continue A;
			}
			insertion.close();
			if (complete != null)
				complete.close();
			if (mappedData == null || mappedData.length % 2 != 0)
				return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	/**
	 * A simple info delete method
	 * 
	 * @param <T>
	 * @param table  is the mySql table in which the data is stored.
	 * @param column the specific column to delete data from.
	 * @param data   the data to be deleted.
	 */
	public static synchronized <T extends Object> void deleteInfo(String table, String column, T data) {
		openConnection();
		try {
			PreparedStatement sql = connection
					.prepareStatement(String.format("DELETE FROM %s WHERE %s=?;", table, column));
			sql.setObject(1, data);
			sql.execute();
			sql.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
}