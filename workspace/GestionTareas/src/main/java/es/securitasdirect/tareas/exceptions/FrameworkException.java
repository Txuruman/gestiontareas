package es.securitasdirect.tareas.exceptions;

/**
 * Excepción para errores RunTime que no deben ser capturados,
 * fundamentalmente para errores de arquitectura y errores irecuperables.
 * Si se desea se puede extender para crear una categoria nueva.
 * 
 * @author Javier Naval
 */
public class FrameworkException extends RuntimeException {
    private static final long serialVersionUID = 1L;
	public FrameworkException (Exception e)
	{
		super(e);
	}
	
	public FrameworkException (RuntimeException e)
	{
		super(e);
	}
	
	public FrameworkException (String message)
	{
		super(message);
	}
	
	public FrameworkException() {
		super();
	}	
}
