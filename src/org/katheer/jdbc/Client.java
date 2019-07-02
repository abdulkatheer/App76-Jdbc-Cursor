package org.katheer.jdbc;

/*
DB : ORACLE 12C
---------------

CREATE A TABLE:
---------------
CREATE TABLE employee(
    eid NUMBER PRIMARY KEY,
    ename VARCHAR2(30) NOT NULL,
    esal FLOAT NOT NULL,
    eaddr VARCHAR2(30) NOT NULL);

INSERT INTO employee VALUES(111, 'AAA', 5000, 'ZZZ');
INSERT INTO employee VALUES(222, 'BBB', 7000, 'YYY');
INSERT INTO employee VALUES(333, 'CCC', 8000, 'XXX');
INSERT INTO employee VALUES(444, 'DDD', 9000, 'WWW');
INSERT INTO employee VALUES(555, 'EEE', 10000, 'VVV');
INSERT INTO employee VALUES(666, 'FFF', 11000, 'UUU');

CURSOR IN PROCEDURE:
--------------------
CREATE OR REPLACE PROCEDURE getEmployees (sal IN FLOAT, employees OUT
SYS_REFCURSOR)
AS
BEGIN
    OPEN employees FOR SELECT * FROM employee WHERE esal > sal;
END getEmployees;
/


CURSOR IN FUNCTION:
-------------------
CREATE OR REPLACE FUNCTION getEmployee (sal IN FLOAT)
RETURN SYS_REFCURSOR
AS
    employees SYS_REFCURSOR;
BEGIN
    OPEN employees FOR SELECT * FROM employee WHERE esal > sal;
    RETURN employees;
END getEmployee;
/


 */

import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Client {
    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.OracleDriver");
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin" +
                ":@localhost:1521:orcl", "system", "katheer");

        /*
        CallableStatement callableStatement = connection.prepareCall("{CALL " +
                "getEmployees(?, ?)}");
        callableStatement.setFloat(1, 7000);
        callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
        callableStatement.execute();

        ResultSet employees = (ResultSet) callableStatement.getObject(2);
        System.out.println("ENO\tENAME\tESAL\tEADDR");
        while (employees.next()) {
            System.out.println(employees.getInt(1) + "\t" + employees.getString(2)
                    + "\t" + employees.getFloat(3) + "\t" + employees.getString(4));
        }
         */

        CallableStatement callableStatement = connection.prepareCall("{? = " +
                "call getEmployee(?)}");
        callableStatement.setFloat(2,7000);
        callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
        callableStatement.execute();

        ResultSet employees = (ResultSet) callableStatement.getObject(1);
        System.out.println("ENO\tENAME\tESAL\tEADDR");
        while (employees.next()) {
            System.out.println(employees.getInt(1) + "\t" + employees.getString(2)
                    + "\t\t" + employees.getFloat(3) + "\t" + employees.getString(4));
        }

    }
}
