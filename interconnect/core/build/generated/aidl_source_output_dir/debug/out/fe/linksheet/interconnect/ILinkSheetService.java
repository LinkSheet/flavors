/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Using: /media/felix/devssd/Android/Sdk/build-tools/35.0.0/aidl -p/media/felix/devssd/Android/Sdk/platforms/android-36/framework.aidl -o/home/felix/dev/projects/LinkSheet/flavors/interconnect/core/build/generated/aidl_source_output_dir/debug/out -I/home/felix/dev/projects/LinkSheet/flavors/interconnect/core/src/main/aidl -I/home/felix/dev/projects/LinkSheet/flavors/interconnect/core/src/debug/aidl -d/tmp/aidl4938163649091553576.d /home/felix/dev/projects/LinkSheet/flavors/interconnect/core/src/main/aidl/fe/linksheet/interconnect/ILinkSheetService.aidl
 */
package fe.linksheet.interconnect;
public interface ILinkSheetService extends android.os.IInterface
{
  /** Default implementation for ILinkSheetService. */
  public static class Default implements fe.linksheet.interconnect.ILinkSheetService
  {
    @Override public void getSelectedDomainsAsync(java.lang.String packageName, fe.linksheet.interconnect.ISelectedDomainsCallback callback) throws android.os.RemoteException
    {
    }
    // Request that the passed domains be set as preferred for the
    // passed package.
    // The ComponentName should point to the Activity handling the domains.
    // Currently, the passed package must match the calling package.
    @Override public void selectDomains(java.lang.String packageName, fe.linksheet.interconnect.StringParceledListSlice domains, android.content.ComponentName componentName) throws android.os.RemoteException
    {
    }
    @Override public void selectDomainsWithCallback(java.lang.String packageName, fe.linksheet.interconnect.StringParceledListSlice domains, android.content.ComponentName componentName, fe.linksheet.interconnect.IDomainSelectionResultCallback callback) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements fe.linksheet.interconnect.ILinkSheetService
  {
    /** Construct the stub at attach it to the interface. */
    @SuppressWarnings("this-escape")
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an fe.linksheet.interconnect.ILinkSheetService interface,
     * generating a proxy if needed.
     */
    public static fe.linksheet.interconnect.ILinkSheetService asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof fe.linksheet.interconnect.ILinkSheetService))) {
        return ((fe.linksheet.interconnect.ILinkSheetService)iin);
      }
      return new fe.linksheet.interconnect.ILinkSheetService.Stub.Proxy(obj);
    }
    @Override public android.os.IBinder asBinder()
    {
      return this;
    }
    @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
    {
      java.lang.String descriptor = DESCRIPTOR;
      if (code >= android.os.IBinder.FIRST_CALL_TRANSACTION && code <= android.os.IBinder.LAST_CALL_TRANSACTION) {
        data.enforceInterface(descriptor);
      }
      if (code == INTERFACE_TRANSACTION) {
        reply.writeString(descriptor);
        return true;
      }
      switch (code)
      {
        case TRANSACTION_getSelectedDomainsAsync:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          fe.linksheet.interconnect.ISelectedDomainsCallback _arg1;
          _arg1 = fe.linksheet.interconnect.ISelectedDomainsCallback.Stub.asInterface(data.readStrongBinder());
          this.getSelectedDomainsAsync(_arg0, _arg1);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_selectDomains:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          fe.linksheet.interconnect.StringParceledListSlice _arg1;
          _arg1 = _Parcel.readTypedObject(data, fe.linksheet.interconnect.StringParceledListSlice.CREATOR);
          android.content.ComponentName _arg2;
          _arg2 = _Parcel.readTypedObject(data, android.content.ComponentName.CREATOR);
          this.selectDomains(_arg0, _arg1, _arg2);
          reply.writeNoException();
          break;
        }
        case TRANSACTION_selectDomainsWithCallback:
        {
          java.lang.String _arg0;
          _arg0 = data.readString();
          fe.linksheet.interconnect.StringParceledListSlice _arg1;
          _arg1 = _Parcel.readTypedObject(data, fe.linksheet.interconnect.StringParceledListSlice.CREATOR);
          android.content.ComponentName _arg2;
          _arg2 = _Parcel.readTypedObject(data, android.content.ComponentName.CREATOR);
          fe.linksheet.interconnect.IDomainSelectionResultCallback _arg3;
          _arg3 = fe.linksheet.interconnect.IDomainSelectionResultCallback.Stub.asInterface(data.readStrongBinder());
          this.selectDomainsWithCallback(_arg0, _arg1, _arg2, _arg3);
          reply.writeNoException();
          break;
        }
        default:
        {
          return super.onTransact(code, data, reply, flags);
        }
      }
      return true;
    }
    private static class Proxy implements fe.linksheet.interconnect.ILinkSheetService
    {
      private android.os.IBinder mRemote;
      Proxy(android.os.IBinder remote)
      {
        mRemote = remote;
      }
      @Override public android.os.IBinder asBinder()
      {
        return mRemote;
      }
      public java.lang.String getInterfaceDescriptor()
      {
        return DESCRIPTOR;
      }
      @Override public void getSelectedDomainsAsync(java.lang.String packageName, fe.linksheet.interconnect.ISelectedDomainsCallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_getSelectedDomainsAsync, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      // Request that the passed domains be set as preferred for the
      // passed package.
      // The ComponentName should point to the Activity handling the domains.
      // Currently, the passed package must match the calling package.
      @Override public void selectDomains(java.lang.String packageName, fe.linksheet.interconnect.StringParceledListSlice domains, android.content.ComponentName componentName) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          _Parcel.writeTypedObject(_data, domains, 0);
          _Parcel.writeTypedObject(_data, componentName, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_selectDomains, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void selectDomainsWithCallback(java.lang.String packageName, fe.linksheet.interconnect.StringParceledListSlice domains, android.content.ComponentName componentName, fe.linksheet.interconnect.IDomainSelectionResultCallback callback) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeString(packageName);
          _Parcel.writeTypedObject(_data, domains, 0);
          _Parcel.writeTypedObject(_data, componentName, 0);
          _data.writeStrongInterface(callback);
          boolean _status = mRemote.transact(Stub.TRANSACTION_selectDomainsWithCallback, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_getSelectedDomainsAsync = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    static final int TRANSACTION_selectDomains = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_selectDomainsWithCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
  }
  /** @hide */
  public static final java.lang.String DESCRIPTOR = "fe.linksheet.interconnect.ILinkSheetService";
  public void getSelectedDomainsAsync(java.lang.String packageName, fe.linksheet.interconnect.ISelectedDomainsCallback callback) throws android.os.RemoteException;
  // Request that the passed domains be set as preferred for the
  // passed package.
  // The ComponentName should point to the Activity handling the domains.
  // Currently, the passed package must match the calling package.
  public void selectDomains(java.lang.String packageName, fe.linksheet.interconnect.StringParceledListSlice domains, android.content.ComponentName componentName) throws android.os.RemoteException;
  public void selectDomainsWithCallback(java.lang.String packageName, fe.linksheet.interconnect.StringParceledListSlice domains, android.content.ComponentName componentName, fe.linksheet.interconnect.IDomainSelectionResultCallback callback) throws android.os.RemoteException;
  /** @hide */
  static class _Parcel {
    static private <T> T readTypedObject(
        android.os.Parcel parcel,
        android.os.Parcelable.Creator<T> c) {
      if (parcel.readInt() != 0) {
          return c.createFromParcel(parcel);
      } else {
          return null;
      }
    }
    static private <T extends android.os.Parcelable> void writeTypedObject(
        android.os.Parcel parcel, T value, int parcelableFlags) {
      if (value != null) {
        parcel.writeInt(1);
        value.writeToParcel(parcel, parcelableFlags);
      } else {
        parcel.writeInt(0);
      }
    }
  }
}
