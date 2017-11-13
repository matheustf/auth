package br.com.money.auth.usuario;

public class Greeting {

    private long id;
    private String content;

    public Greeting() {
	}
    
    public Greeting(String content) {
		this.content = content;
	}

	public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

	public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
