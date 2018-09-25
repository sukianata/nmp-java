 
namespace java com.fiberhome.nmpDataBusPlugin.thrift

include "fileState.thrift"
include "../NMPDataService/exception.thrift"
service FileService {
	bool recvFileState(1:string fullname, 2:fileState.FileState fileState) throws(1: exception.RPCApplicationException err);
}