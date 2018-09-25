/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.fiberhome.nmpDataBusPlugin.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2018-09-19")
public class DiskInfo implements org.apache.thrift.TBase<DiskInfo, DiskInfo._Fields>, java.io.Serializable, Cloneable, Comparable<DiskInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DiskInfo");

  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("Name", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("Description", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TOTAL_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("TotalSize", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField FREE_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("FreeSize", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DiskInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DiskInfoTupleSchemeFactory());
  }

  public String Name; // required
  public String Description; // required
  public long TotalSize; // required
  public long FreeSize; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    NAME((short)1, "Name"),
    DESCRIPTION((short)2, "Description"),
    TOTAL_SIZE((short)3, "TotalSize"),
    FREE_SIZE((short)4, "FreeSize");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // NAME
          return NAME;
        case 2: // DESCRIPTION
          return DESCRIPTION;
        case 3: // TOTAL_SIZE
          return TOTAL_SIZE;
        case 4: // FREE_SIZE
          return FREE_SIZE;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __TOTALSIZE_ISSET_ID = 0;
  private static final int __FREESIZE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("Name", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("Description", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TOTAL_SIZE, new org.apache.thrift.meta_data.FieldMetaData("TotalSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.FREE_SIZE, new org.apache.thrift.meta_data.FieldMetaData("FreeSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DiskInfo.class, metaDataMap);
  }

  public DiskInfo() {
  }

  public DiskInfo(
    String Name,
    String Description,
    long TotalSize,
    long FreeSize)
  {
    this();
    this.Name = Name;
    this.Description = Description;
    this.TotalSize = TotalSize;
    setTotalSizeIsSet(true);
    this.FreeSize = FreeSize;
    setFreeSizeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DiskInfo(DiskInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetName()) {
      this.Name = other.Name;
    }
    if (other.isSetDescription()) {
      this.Description = other.Description;
    }
    this.TotalSize = other.TotalSize;
    this.FreeSize = other.FreeSize;
  }

  public DiskInfo deepCopy() {
    return new DiskInfo(this);
  }

  @Override
  public void clear() {
    this.Name = null;
    this.Description = null;
    setTotalSizeIsSet(false);
    this.TotalSize = 0;
    setFreeSizeIsSet(false);
    this.FreeSize = 0;
  }

  public String getName() {
    return this.Name;
  }

  public DiskInfo setName(String Name) {
    this.Name = Name;
    return this;
  }

  public void unsetName() {
    this.Name = null;
  }

  /** Returns true if field Name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.Name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.Name = null;
    }
  }

  public String getDescription() {
    return this.Description;
  }

  public DiskInfo setDescription(String Description) {
    this.Description = Description;
    return this;
  }

  public void unsetDescription() {
    this.Description = null;
  }

  /** Returns true if field Description is set (has been assigned a value) and false otherwise */
  public boolean isSetDescription() {
    return this.Description != null;
  }

  public void setDescriptionIsSet(boolean value) {
    if (!value) {
      this.Description = null;
    }
  }

  public long getTotalSize() {
    return this.TotalSize;
  }

  public DiskInfo setTotalSize(long TotalSize) {
    this.TotalSize = TotalSize;
    setTotalSizeIsSet(true);
    return this;
  }

  public void unsetTotalSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALSIZE_ISSET_ID);
  }

  /** Returns true if field TotalSize is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalSize() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALSIZE_ISSET_ID);
  }

  public void setTotalSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALSIZE_ISSET_ID, value);
  }

  public long getFreeSize() {
    return this.FreeSize;
  }

  public DiskInfo setFreeSize(long FreeSize) {
    this.FreeSize = FreeSize;
    setFreeSizeIsSet(true);
    return this;
  }

  public void unsetFreeSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FREESIZE_ISSET_ID);
  }

  /** Returns true if field FreeSize is set (has been assigned a value) and false otherwise */
  public boolean isSetFreeSize() {
    return EncodingUtils.testBit(__isset_bitfield, __FREESIZE_ISSET_ID);
  }

  public void setFreeSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FREESIZE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
        setDescription((String)value);
      }
      break;

    case TOTAL_SIZE:
      if (value == null) {
        unsetTotalSize();
      } else {
        setTotalSize((Long)value);
      }
      break;

    case FREE_SIZE:
      if (value == null) {
        unsetFreeSize();
      } else {
        setFreeSize((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case NAME:
      return getName();

    case DESCRIPTION:
      return getDescription();

    case TOTAL_SIZE:
      return getTotalSize();

    case FREE_SIZE:
      return getFreeSize();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case NAME:
      return isSetName();
    case DESCRIPTION:
      return isSetDescription();
    case TOTAL_SIZE:
      return isSetTotalSize();
    case FREE_SIZE:
      return isSetFreeSize();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DiskInfo)
      return this.equals((DiskInfo)that);
    return false;
  }

  public boolean equals(DiskInfo that) {
    if (that == null)
      return false;

    boolean this_present_Name = true && this.isSetName();
    boolean that_present_Name = true && that.isSetName();
    if (this_present_Name || that_present_Name) {
      if (!(this_present_Name && that_present_Name))
        return false;
      if (!this.Name.equals(that.Name))
        return false;
    }

    boolean this_present_Description = true && this.isSetDescription();
    boolean that_present_Description = true && that.isSetDescription();
    if (this_present_Description || that_present_Description) {
      if (!(this_present_Description && that_present_Description))
        return false;
      if (!this.Description.equals(that.Description))
        return false;
    }

    boolean this_present_TotalSize = true;
    boolean that_present_TotalSize = true;
    if (this_present_TotalSize || that_present_TotalSize) {
      if (!(this_present_TotalSize && that_present_TotalSize))
        return false;
      if (this.TotalSize != that.TotalSize)
        return false;
    }

    boolean this_present_FreeSize = true;
    boolean that_present_FreeSize = true;
    if (this_present_FreeSize || that_present_FreeSize) {
      if (!(this_present_FreeSize && that_present_FreeSize))
        return false;
      if (this.FreeSize != that.FreeSize)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_Name = true && (isSetName());
    list.add(present_Name);
    if (present_Name)
      list.add(Name);

    boolean present_Description = true && (isSetDescription());
    list.add(present_Description);
    if (present_Description)
      list.add(Description);

    boolean present_TotalSize = true;
    list.add(present_TotalSize);
    if (present_TotalSize)
      list.add(TotalSize);

    boolean present_FreeSize = true;
    list.add(present_FreeSize);
    if (present_FreeSize)
      list.add(FreeSize);

    return list.hashCode();
  }

  @Override
  public int compareTo(DiskInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.Name, other.Name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDescription()).compareTo(other.isSetDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.Description, other.Description);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalSize()).compareTo(other.isSetTotalSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.TotalSize, other.TotalSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFreeSize()).compareTo(other.isSetFreeSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFreeSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.FreeSize, other.FreeSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("DiskInfo(");
    boolean first = true;

    sb.append("Name:");
    if (this.Name == null) {
      sb.append("null");
    } else {
      sb.append(this.Name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("Description:");
    if (this.Description == null) {
      sb.append("null");
    } else {
      sb.append(this.Description);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("TotalSize:");
    sb.append(this.TotalSize);
    first = false;
    if (!first) sb.append(", ");
    sb.append("FreeSize:");
    sb.append(this.FreeSize);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DiskInfoStandardSchemeFactory implements SchemeFactory {
    public DiskInfoStandardScheme getScheme() {
      return new DiskInfoStandardScheme();
    }
  }

  private static class DiskInfoStandardScheme extends StandardScheme<DiskInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DiskInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.Name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.Description = iprot.readString();
              struct.setDescriptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOTAL_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.TotalSize = iprot.readI64();
              struct.setTotalSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FREE_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.FreeSize = iprot.readI64();
              struct.setFreeSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, DiskInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.Name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.Name);
        oprot.writeFieldEnd();
      }
      if (struct.Description != null) {
        oprot.writeFieldBegin(DESCRIPTION_FIELD_DESC);
        oprot.writeString(struct.Description);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TOTAL_SIZE_FIELD_DESC);
      oprot.writeI64(struct.TotalSize);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FREE_SIZE_FIELD_DESC);
      oprot.writeI64(struct.FreeSize);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DiskInfoTupleSchemeFactory implements SchemeFactory {
    public DiskInfoTupleScheme getScheme() {
      return new DiskInfoTupleScheme();
    }
  }

  private static class DiskInfoTupleScheme extends TupleScheme<DiskInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DiskInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetName()) {
        optionals.set(0);
      }
      if (struct.isSetDescription()) {
        optionals.set(1);
      }
      if (struct.isSetTotalSize()) {
        optionals.set(2);
      }
      if (struct.isSetFreeSize()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetName()) {
        oprot.writeString(struct.Name);
      }
      if (struct.isSetDescription()) {
        oprot.writeString(struct.Description);
      }
      if (struct.isSetTotalSize()) {
        oprot.writeI64(struct.TotalSize);
      }
      if (struct.isSetFreeSize()) {
        oprot.writeI64(struct.FreeSize);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DiskInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.Name = iprot.readString();
        struct.setNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.Description = iprot.readString();
        struct.setDescriptionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.TotalSize = iprot.readI64();
        struct.setTotalSizeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.FreeSize = iprot.readI64();
        struct.setFreeSizeIsSet(true);
      }
    }
  }

}
