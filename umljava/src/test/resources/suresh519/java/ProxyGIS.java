


public class ProxyGIS implements IGISInterface {
	public MoveFunctionCls moveFunctionCls;
	public ProxyGIS() {
		this.moveFunctionCls = new MoveFunctionCls();
	}
	public MoveFunctionCls getMoveFunctionCls() {
		return this.moveFunctionCls;
	}
	public void setMoveFunctionCls(MoveFunctionCls moveFunctionCls) {
		if (moveFunctionCls != null)this.moveFunctionCls = moveFunctionCls;
	}
}



