 package ppss;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

 /* IMPORTANTE:
     Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
     vamos a usar "throws Esception" en los métodos, para que el código quede más
     legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit
     con un assertDoesNotThrow()
     Es decir, que vamos a primar la legibilidad de los tests.
     Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
     invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
 */
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {

    databaseTester = new MiJdbcDatabaseTester(
            "com.mysql.cj.jdbc.Driver",
            "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false",
            "root", "david");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
     //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_insert_should_not_allow_duplicate_when_same_user_is_inserted() throws Exception {
    Cliente cliente = new Cliente(2,"David","Znojek");
    cliente.setDireccion("wowo");
    cliente.setCiudad("Aachen");

    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-esperadoD3.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    SQLException sqlex = Assertions.assertThrows(SQLException.class, ()->clienteDAO.insert(cliente));
    assertEquals("Duplicate entry '2' for key 'cliente.PRIMARY'", sqlex.getMessage());
  }

  @Test
  public void D4_delete_should_not_delete_unexistent_client_when_client_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(3, "Daniel", "Hipolito");
    cliente.setDireccion("bit");
    cliente.setCiudad("España");

    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-esperadoD3.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    SQLException sqlex = Assertions.assertThrows(SQLException.class, ()->clienteDAO.delete(cliente));
    assertEquals("Delete failed!", sqlex.getMessage());
  }

  @Test
  public void D5_update_shall_modify_client_when_client_exists() throws Exception {
    Cliente modcli = new Cliente(1,"John","Smith");
    modcli.setDireccion("Other Street");
    modcli.setCiudad("NewCity");

    IDataSet dataset = new FlatXmlDataFileLoader().load("/cliente-init-D5.xml");
    databaseTester.setDataSet(dataset);
    databaseTester.onSetup();

    Assertions.assertDoesNotThrow(() -> clienteDAO.update(modcli));

    IDataSet storage = connection.createDataSet();
    ITable clientes = storage.getTable("cliente");

    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-D5.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable,clientes);
  }

  @Test
  public void D6_retrieve_should_retrieve_user_data_when_client_requested() throws Exception {
    int cliente_ID = 1;
    Cliente expected =  new Cliente(1,"John", "Smith");
    expected.setDireccion("1 Main Street");
    expected.setCiudad("Anycity");

    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    Cliente cliente = Assertions.assertDoesNotThrow(()-> clienteDAO.retrieve(cliente_ID));

    Assertions.assertAll(
            "comparacion datos del cliente obtenido de DB",
            () -> assertEquals(expected.getId(),cliente.getId()),
            () -> assertEquals(expected.getNombre(),cliente.getNombre()),
            () -> assertEquals(expected.getApellido(),cliente.getApellido()),
            () -> assertEquals(expected.getCiudad(),cliente.getCiudad()),
            () -> assertEquals(expected.getDireccion(),cliente.getDireccion())
    );
  }
}
