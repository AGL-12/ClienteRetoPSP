package cliente;

public class Mensaje {
	private String contenido;
	private String emisor;
	private String destinatario;
	private boolean privado;

	public Mensaje(String contenido, String emisor, String destinatario, boolean privado) {
		super();
		this.contenido = contenido;
		this.emisor = emisor;
		this.destinatario = destinatario;
		this.privado = privado;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public boolean isPrivado() {
		return privado;
	}

	public void setPrivado(boolean privado) {
		this.privado = privado;
	}

}
