


public class ProxyDB implements IProxyDB {
	public MoveFunctionCls moveFunctionCls;
	public ProxyDB() {
		this.moveFunctionCls = new MoveFunctionCls();
	}
	public MoveFunctionCls getMoveFunctionCls() {
		return this.moveFunctionCls;
	}
	public void setMoveFunctionCls(MoveFunctionCls moveFunctionCls) {
		if (moveFunctionCls != null)this.moveFunctionCls = moveFunctionCls;
	}
}



