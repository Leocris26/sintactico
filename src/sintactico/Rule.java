package sintactico;

public class Rule {
	public boolean existe;
	public boolean epsilon;
	public int[] regla;
	public Rule(int[] regla,boolean existe,boolean epsilon) {
		this.regla = regla;
		this.existe = existe;
		this.epsilon = epsilon;
	}
}
