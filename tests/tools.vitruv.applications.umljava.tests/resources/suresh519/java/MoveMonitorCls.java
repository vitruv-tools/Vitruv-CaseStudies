


public class MoveMonitorCls extends TimerTask {
	public ArrayList moveOrdersList;
	public MoveFunctionCls moveFunctionCls;
	public MoveMonitorCls() {
		this.moveFunctionCls = new MoveFunctionCls();
	}
	public MoveFunctionCls getMoveFunctionCls() {
		return this.moveFunctionCls;
	}
	public void setMoveFunctionCls(MoveFunctionCls moveFunctionCls) {
		if (moveFunctionCls != null)this.moveFunctionCls = moveFunctionCls;
	}
	public void processMoveOrders() {
	}
}



