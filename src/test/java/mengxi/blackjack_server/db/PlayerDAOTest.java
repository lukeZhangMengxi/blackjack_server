package mengxi.blackjack_server.db;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import mengxi.blackjack_server.db.dao.PlayerDAOImpl;
import mengxi.blackjack_server.db.entity.Player;

@SuppressWarnings("serial")
public class PlayerDAOTest {

	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	private PlayerDAOImpl playerDAO = new PlayerDAOImpl(new NamedParameterJdbcTemplate(getDataSource()));

	@BeforeClass
	public static void createSchema() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, "/Users/zhangmengxi/workspace/blackjack_server/db/psql_schema.sql",
				StandardCharsets.UTF_8, false);
	}

	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsert(dataSet);
	}

	private IDataSet readDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(
				"/Users/zhangmengxi/workspace/blackjack_server/src/test/java/mengxi/blackjack_server/db/data.xml"));
	}

	private void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	private DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(JDBC_URL);
		dataSource.setUser(USER);
		dataSource.setPassword(PASSWORD);
		return dataSource;
	}

	@Test
	public void selectAllTest() {
		List<Player> actual = playerDAO.getAll();

		List<Player> expected = new ArrayList<Player>() {
			{
				this.add(new Player("8730ba8d-cba1-4e6b-a6da-d463727a57c9", "Larry", 100));
				this.add(new Player("766a84e7-22bd-4ed5-aacd-203a5b47a49e", "LOL", 500));
				this.add(new Player("2d896eb0-cfee-4196-8b9c-d7f5aa464f1c", "Lee", 1100));
			}
		};

		assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i), actual.get(i));
		}
	}

	@Test
	public void selectOnePlayerTest() {
		Player actual = playerDAO.getPlayer(UUID.fromString("8730ba8d-cba1-4e6b-a6da-d463727a57c9"));
		Player expected = new Player("8730ba8d-cba1-4e6b-a6da-d463727a57c9", "Larry", 100);
		assertEquals(expected, actual);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void selectNonExistingPlayerTest() {
		UUID nonExist = UUID.fromString("940ac3b6-9c12-4c40-b63b-dd5f75427461");
		playerDAO.getPlayer(nonExist);
	}

	@Test
	public void selectDepositTest() {
		long actual = playerDAO.getDeposit(UUID.fromString("8730ba8d-cba1-4e6b-a6da-d463727a57c9"));
		assertEquals(100, actual);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void selectNonExistingPlayerDepositTest() {
		UUID nonExist = UUID.fromString("940ac3b6-9c12-4c40-b63b-dd5f75427461");
		playerDAO.getDeposit(nonExist);
	}

	@Test
	public void updateDepositTest() {
		UUID playerId = UUID.fromString("8730ba8d-cba1-4e6b-a6da-d463727a57c9");
		long deposit = playerDAO.getDeposit(playerId);
		assertEquals(100, deposit);

		playerDAO.updateDeposit(playerId, 220);
		deposit = playerDAO.getDeposit(playerId);
		assertEquals(320, deposit);

		// Note: bounding check is on PlayerService layer
		playerDAO.updateDeposit(playerId, -500);
		deposit = playerDAO.getDeposit(playerId);
		assertEquals(-180, deposit);

	}

}
