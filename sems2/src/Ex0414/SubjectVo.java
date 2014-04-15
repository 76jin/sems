package Ex0414;

/* setter/getter 적용 <= 캡슐화
 */
public class SubjectVo {
	private int 				no;				// DB 컬럼과 연결. 컬럼명 사용하지 말것. 일반 용어 사용.
	private String 		title;   // 다른 프로젝트에서 재사용할 때 제약이 없다.
	private String 		description;
	
	public int getNo() {
		return no;
	}
	
	public SubjectVo setNo(int no) {
		this.no = no;
		return this;
	}
	
	public String getTitle() {
		return title;
	}
	
	public SubjectVo setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getDescription() {
		return description;
	}
	
	public SubjectVo setDescription(String description) {
		this.description = description;
		return this;
	}

	@Override
  public String toString() {
	  return "SubjectVo [no=" + no + ", title=" + title + ", description="
	      + description + "]";
  }
	
}
