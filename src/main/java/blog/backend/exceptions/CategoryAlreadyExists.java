package blog.backend.exceptions;

public class CategoryAlreadyExists extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public CategoryAlreadyExists(String message) {
    super(message);
  }

}
