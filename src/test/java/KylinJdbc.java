import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class KylinJdbc {
    public void connentJdbc() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Driver driver = (Driver) Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
        Properties info = new Properties();
        info.put("user", "admin");
        info.put("password", "KYLIN");
        Connection conn = driver.connect("jdbc:kylin://10.228.1.29:7070/learn_kylin", info);
        Statement state = conn.createStatement();
        ResultSet resultSet = state.executeQuery("select part_dt, sum(price) as total_selled, count(distinct seller_id) as sellers from kylin_sales group by part_dt order by part_dt");
        while (resultSet.next()) {
            resultSet.getString(1);
            System.out.println(resultSet.getString(1)+"  "+resultSet.getString(2)+"  "+resultSet.getString(3));
        }
    }
    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        KylinJdbc ky = new KylinJdbc();
        ky.connentJdbc();
    }
}