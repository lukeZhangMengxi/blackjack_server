package mengxi.blackjack_server.db;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import mengxi.blackjack_server.db.dao.PlayerDAOImpl;
import mengxi.blackjack_server.db.entity.Player;

@SuppressWarnings("serial")
public class MyTest {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	private PlayerDAOImpl playerDAO = new PlayerDAOImpl(
		new NamedParameterJdbcTemplate(getDataSource())
	);

	@BeforeClass
	public static void createSchema() throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, "/Users/zhangmengxi/workspace/blackjack_server/db/psql_schema.sql", StandardCharsets.UTF_8, false);
	}

	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsert(dataSet);
	}

	private IDataSet readDataSet() throws Exception {
		return new FlatXmlDataSetBuilder()
			.build(new File("/Users/zhangmengxi/workspace/blackjack_server/src/test/java/mengxi/blackjack_server/db/data.xml"));
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
	public void selectAllTest() throws Exception {
		List<Player> actual = playerDAO.getAll();
        
        List<Player> expected = new ArrayList<Player>() {{
			this.add(new Player("38373330-6261-3864-2d63-6261312d3465", "Larry", 100));
			this.add(new Player("38373330-6261-3864-2d63-6261322d3465", "LOL", 500));
			this.add(new Player("38373330-6261-3864-2d63-6261332d3465", "Lee", 1100));
        }};

        assertEquals(expected.size(), actual.size());
        for (int i=0; i<expected.size(); i++) {
            assertEquals(expected.get(i), actual.get(i));
        }
	}

}
