namespace java com.fiberhome.nmpDataBusPlugin.thrift

exception RPCApplicationException {   
  1: string Message;
  2: string RPCStackTrace;
  3: i32 ErrorCode;
}  