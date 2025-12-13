/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Using: /media/felix/devssd/Android/Sdk/build-tools/35.0.0/aidl -p/media/felix/devssd/Android/Sdk/platforms/android-36/framework.aidl -o/home/felix/dev/projects/LinkSheet/flavors/interconnect/core/build/generated/aidl_source_output_dir/debug/out -I/home/felix/dev/projects/LinkSheet/flavors/interconnect/core/src/main/aidl -I/home/felix/dev/projects/LinkSheet/flavors/interconnect/core/src/debug/aidl -d/tmp/aidl13686072369531084533.d /home/felix/dev/projects/LinkSheet/flavors/interconnect/core/src/main/aidl/fe/linksheet/interconnect/IDomainSelectionResultCallback.aidl
 */
package fe.linksheet.interconnect;
public interface IDomainSelectionResultCallback extends android.os.IInterface
{
  /** Default implementation for IDomainSelectionResultCallback. */
  public static class Default implements fe.linksheet.interconnect.IDomainSelectionResultCallback
  {
    @Override public void onDomainSelectionConfirmed() throws android.os.RemoteException
    {
    }
    @Override public void onDomainSelectionCancelled() throws android.os.RemoteException
    {
    }
    @Override public void onSomeDomainsSelected(fe.linksheet.interconnect.StringParceledListSlice selectedDomains) throws android.os.RemoteException
    {
    }
    @Override
    public android.os.IBinder asBinder() {
      return null;
    }
  }
  /** Local-side IPC implementation stub class. */
  public static abstract class Stub extends android.os.Binder implements fe.linksheet.interconnect.IDomainSelectionResultCallback
  {
    /** Construct the stub at attach it to the interface. */
    @SuppressWarnings("this-escape")
    public Stub()
    {
      this.attachInterface(this, DESCRIPTOR);
    }
    /**
     * Cast an IBinder object into an fe.linksheet.interconnect.IDomainSelectionResultCallback interface,
     * generating a proxy if needed.
     */
    public static fe.linksheet.interconnect.IDomainSelectionResultCallback asInterface(android.os.IBinder obj)
    {
      if ((obj==null)) {
        return null;
      }
      android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (((iin!=null)&&(iin instanceof fe.linksheet.interconnect.IDomainSelectionResultCallback))) {
        return ((fe.linksheet.interconnect.IDomainSelectionResultCallback)iin);
      }
      return new fe.linksheet.interconnect.IDomainSelectionResultCallback.Stub.Proxy(obj);
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
        case TRANSACTION_onDomainSelectionConfirmed:
        {
          this.onDomainSelectionConfirmed();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_onDomainSelectionCancelled:
        {
          this.onDomainSelectionCancelled();
          reply.writeNoException();
          break;
        }
        case TRANSACTION_onSomeDomainsSelected:
        {
          fe.linksheet.interconnect.StringParceledListSlice _arg0;
          _arg0 = _Parcel.readTypedObject(data, fe.linksheet.interconnect.StringParceledListSlice.CREATOR);
          this.onSomeDomainsSelected(_arg0);
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
    private static class Proxy implements fe.linksheet.interconnect.IDomainSelectionResultCallback
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
      @Override public void onDomainSelectionConfirmed() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDomainSelectionConfirmed, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void onDomainSelectionCancelled() throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onDomainSelectionCancelled, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
      @Override public void onSomeDomainsSelected(fe.linksheet.interconnect.StringParceledListSlice selectedDomains) throws android.os.RemoteException
      {
        android.os.Parcel _data = android.os.Parcel.obtain();
        android.os.Parcel _reply = android.os.Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _Parcel.writeTypedObject(_data, selectedDomains, 0);
          boolean _status = mRemote.transact(Stub.TRANSACTION_onSomeDomainsSelected, _data, _reply, 0);
          _reply.readException();
        }
        finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }
    static final int TRANSACTION_onDomainSelectionConfirmed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    static final int TRANSACTION_onDomainSelectionCancelled = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
    static final int TRANSACTION_onSomeDomainsSelected = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
  }
  /** @hide */
  public static final java.lang.String DESCRIPTOR = "fe.linksheet.interconnect.IDomainSelectionResultCallback";
  public void onDomainSelectionConfirmed() throws android.os.RemoteException;
  public void onDomainSelectionCancelled() throws android.os.RemoteException;
  public void onSomeDomainsSelected(fe.linksheet.interconnect.StringParceledListSlice selectedDomains) throws android.os.RemoteException;
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
