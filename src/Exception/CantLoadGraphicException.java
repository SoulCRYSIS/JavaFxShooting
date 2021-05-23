package Exception;

public class CantLoadGraphicException extends Exception {
	public CantLoadGraphicException(String path) {
		super("Can't load " + path);
	}
}
