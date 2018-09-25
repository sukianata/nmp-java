package ThriftApplication;

import com.fiberhome.nmpDataBusPlugin.DataServiceImp;
import com.fiberhome.nmpDataBusPlugin.FileServiceImpl;
import com.fiberhome.nmpDataBusPlugin.XDataBus;
import com.fiberhome.nmpDataBusPlugin.thrift.FileService;
import com.fiberhome.nmpDataBusPlugin.thrift.NMPDataService;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黃帆
 * @date 2018/9/20 8:49
 */
public class ThriftApplication {
    private  static  final int PORT1=9897;
    private  static  final int PORT2=9797;
    private  static  final String rootDir = "E:\\filemonitortest";
    public static void main(String[] args) {
        XDataBus bus= new XDataBus();
        try {
            Map<String,String> params=new HashMap<>();
            params.put("port","9898");
            params.put("timeout","30000");
            params.put("fileServiceServiceName","com.fiberhome.nmpDataBusPlugin.thrift.FileService");
            bus.setParams(params);
            bus.start();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
    public static void openAServer(){
        System.out.println(">>>>>>>>>>>>>>A开启服务端>>>>>>>>>>>");
        TProcessor tProcessor=new FileService.Processor<FileService.Iface>(new FileServiceImpl());
        try (TServerSocket serverSocket = new TServerSocket(PORT2)) {
            TServer.Args tArgs=new TServer.Args(serverSocket);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server=new TSimpleServer(tArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
    public static void openBServer(){
        System.out.println(">>>>>>>>>>>>>>B开启服务端>>>>>>>>>>>");
        TProcessor tProcessor=new NMPDataService.Processor<NMPDataService.Iface>(new DataServiceImp());
        try (TServerSocket serverSocket = new TServerSocket(PORT1)) {
            TServer.Args tArgs=new TServer.Args(serverSocket);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server=new TSimpleServer(tArgs);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
    public static void openAClient(){
        System.out.println(">>>>>>>>>>>>>A开启客户端>>>>>>>>>>>>");
        TTransport transport =null;
        try {
            transport = new TSocket("localhost", PORT1, 300000);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            NMPDataService.Client client = new NMPDataService.Client(protocol);
            transport.open();
            client.RegisterFolderWatcher(rootDir,"",true,"127.0.0.1", PORT2);
            String result = client.GetNMPSN();
            System.out.println(result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
