package com.fiberhome.nmpDataBusPlugin;

import com.fiberhome.nmpDataBusPlugin.thrift.FileService;
import com.fiberhome.nmpDataBusPlugin.thrift.FileState;
import com.fiberhome.nmpDataBusPlugin.thrift.RPCApplicationException;
import org.apache.thrift.TException;

/**
 * @author 黃帆
 * @date 2018/9/19 17:32
 */
public class FileServiceImpl implements FileService.Iface {
    @Override
    public boolean recvFileState(String fullname, FileState fileState) throws RPCApplicationException, TException {
        System.out.println(">>>>the file:"+fullname+",the state:"+fileState);
        return false;
    }
}
