import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.sql.*;

import org.postgresql.Driver;

import java.util.LinkedList;

@ManagedBean(name="ControllerBean",eager = true)
@ApplicationScoped
public class ControllerBean implements Serializable {
    private double x;
    private double y;
    private double r;
    private String ex;
    private Connection connection;
    private LinkedList<Point> history;
    @PostConstruct
    public void init(){
        history = new LinkedList<>();
        history.add(new Point(1,1,1));
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/postgres","postgres", "postgres");
        }catch (Exception e){e.printStackTrace();}
    }
    private String resultTable;
    private String firstRow = "<td>X</td> <td>Y</td> <td> R </td> <td>Result</td>";
    public void setResultTable(String resultTable) {this.resultTable = resultTable;}
    public String getResultTable() {
        try {
            Statement statement1 = connection.createStatement();
            ResultSet result1 = statement1.executeQuery("SELECT * FROM points");
            StringBuilder build = new StringBuilder();
            build.append("<table style=\'border-radius: 5px;\' border=\"2\" bordercolor=\"yellow\" id=\"resultTable\">");
            build.append("<tr>" + firstRow + "</tr>");
            while (result1.next()) {
                build.append("<tr>");
                build.append("<td>" + result1.getDouble("x") + "</td><td>" + result1.getDouble("y") + "</td> <td>" + result1.getDouble("r") +
                        "</td>");
                build.append("<td>" + result1.getBoolean("inArea") + "</td>");
                build.append("</tr>");
            }
            build.append("</table>");
            resultTable = build.toString();
        }catch (SQLException e){}
        return resultTable;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getR() {
        return r;
    }
    public void setR(double r) {
        this.r = r;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void submit(){
        Point point = new Point(x,y,r);
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO points values(?,?,?,?)");
            statement.setDouble(1,x);
            statement.setDouble(2,y);
            statement.setDouble(3,r);
            statement.setBoolean(4,point.isInArea());
            statement.executeUpdate();
        }catch (SQLException e){
            ex = e.getSQLState();
        }
    }
    public void clear(){
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("TRUNCATE TABLE points");
        }catch (SQLException e){}
    }
}