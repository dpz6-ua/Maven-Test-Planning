import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;
import org.junit.jupiter.api.*;
import ppss.matriculacion.dao.DAOException;
import ppss.matriculacion.dao.FactoriaDAO;
import ppss.matriculacion.to.AlumnoTO;

import java.time.LocalDate;

public class AlumnoDAOIT {
    IDatabaseTester tester;
    IDatabaseConnection connection;
    AlumnoTO alumno;
    FactoriaDAO fact;

    @BeforeEach
    public void setUp() throws Exception {
        tester = new MiJdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/matriculacion?useSSL=false",
                "root", "david");
        connection = tester.getConnection();
        alumno = new AlumnoTO();
        fact = new FactoriaDAO();
    }

    @Test
    public void A1_addAlumno_should_add_alumno_when_alumno_does_not_exist() throws Exception {
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Aguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985, 2, 22));

        IDataSet entrada = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(entrada);
        tester.onSetup();

        Assertions.assertDoesNotThrow(() -> fact.getAlumnoDAO().addAlumno(alumno));

        IDataSet dataSet = connection.createDataSet();
        ITable salida = dataSet.getTable("alumnos");

        IDataSet expected = new FlatXmlDataFileLoader().load("/tabla3.xml");
        ITable expectedTable = expected.getTable("alumnos");

        Assertion.assertEquals(expectedTable, salida);
    }

    @Test
    public void A2_addAlumno_should_not_add_alumno_when_connection_lost() throws Exception {
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        IDataSet entrada = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(entrada);
        tester.onSetup();

        connection.close();

        DAOException ex = Assertions.assertThrows(DAOException.class, () -> fact.getAlumnoDAO().addAlumno(alumno));
        Assertions.assertEquals("Error al conectar con BD", ex.getMessage());
    }

    @Test
    public void A3_addAlumno_should_not_add_alumno_when_name_null() throws Exception {
        alumno.setNif("11111111A");
        alumno.setNombre(null);
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        IDataSet entrada = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(entrada);
        tester.onSetup();

        DAOException ex = Assertions.assertThrows(DAOException.class, () -> fact.getAlumnoDAO().addAlumno(alumno));
        Assertions.assertEquals("Error al conectar con BD", ex.getMessage());
    }

    @Test
    public void A4_addAlumno_should_not_add_alumno_when_alumno_null() throws Exception {
        alumno = null;

        IDataSet entrada = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(entrada);
        tester.onSetup();

        DAOException ex = Assertions.assertThrows(DAOException.class, () -> fact.getAlumnoDAO().addAlumno(null));
        Assertions.assertEquals("Alumno nulo", ex.getMessage());
    }

    @Test
    public void A5_addAlumno_should_not_add_alumno_when_DB_failed() throws Exception {
        alumno.setNif(null);
        alumno.setNombre("Pedro Garcia Lopez");
        alumno.setFechaNacimiento(LocalDate.of(1982, 2, 22));

        IDataSet dataset = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(dataset);
        tester.onSetup();

        connection.close();

        DAOException de = Assertions.assertThrows(DAOException.class, () -> fact.getAlumnoDAO().addAlumno(alumno));
        Assertions.assertEquals("Error al conectar con BD", de.getMessage());
    }

    @Test
    public void B1_delAlumno_should_delete_alumno_when_exists() throws Exception {
        String nif = "11111111A";

        IDataSet dataset = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(dataset);
        tester.onSetup();

        Assertions.assertDoesNotThrow(() -> fact.getAlumnoDAO().delAlumno(nif));

        IDataSet result = connection.createDataSet();
        ITable table_result = result.getTable("alumnos");

        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla4.xml");
        ITable table_expected = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(table_expected, table_result);
    }

    public void B2_delAlumno_should_throw_exception_when_alumno_does_not_exist() throws Exception {
        String nif = "33333333C";

        IDataSet dataset = new FlatXmlDataFileLoader().load("/tabla2.xml");
        tester.setDataSet(dataset);
        tester.onSetup();

        DAOException ex = Assertions.assertThrows(DAOException.class, () -> fact.getAlumnoDAO().delAlumno(nif));
        Assertions.assertEquals("No se ha borrado ningun alumno", ex.getMessage());
    }


}
