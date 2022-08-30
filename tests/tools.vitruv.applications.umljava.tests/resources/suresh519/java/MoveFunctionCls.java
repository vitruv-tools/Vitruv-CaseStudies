


public class MoveFunctionCls {
	public ProxyDB proxyDB;
	public ProxyGIS proxyGIS;
	public MoveMonitorCls moveExecutorCls;
	public MsgCls msgCls;
	public MoveFunctionCls() {
		this.proxyDB = new ProxyDB();
		this.proxyGIS = new ProxyGIS();
		this.moveExecutorCls = new MoveMonitorCls();
	}
	public ProxyDB getProxyDB() {
		return this.proxyDB;
	}
	public void setProxyDB(ProxyDB proxyDB) {
		if (proxyDB != null)this.proxyDB = proxyDB;
	}
	public ProxyGIS getProxyGIS() {
		return this.proxyGIS;
	}
	public void setProxyGIS(ProxyGIS proxyGIS) {
		if (proxyGIS != null)this.proxyGIS = proxyGIS;
	}
	public MoveMonitorCls getMoveExecutorCls() {
		return this.moveExecutorCls;
	}
	public void setMoveExecutorCls(MoveMonitorCls moveExecutorCls) {
		if (moveExecutorCls != null)this.moveExecutorCls = moveExecutorCls;
	}
	public void calculateNextLocation() {
	}
}



