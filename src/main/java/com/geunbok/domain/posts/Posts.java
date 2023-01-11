/*Posts 클래스는 실제 DB의 테이블과 매칭될 클래스. 보통 Entity 클래스라고도 함 - Entity 클래스의 수정을 통해 작업.
Entity 클래스(Posts 클래스)에서는 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지 코드상으로 명확하게 구분할 수 없기 때문에
절대 Setter 메소드를 만들지 않는다.
Setter가 없는 상황에서 어떻게 값을 채워 DB에 삽입하나?
기본적인 구조는 생성자를 통해 최종값을 채운 후 DB에 삽입.
값 변경이 필요한 경우 해당 이벤트에 맞는 public 메소드를 호출하여 변경하는 것을 전제.
이 책에서는 생성자 대신에 @Builder를 제공하는 빌더 클래스를 사용: 생성자나 빌더나 생성 시점에 값을 채워주는 역할은 똑같음 다만, 생성자의 경우
지금 채워야 할 필드가 무엇인지 명확히 지정할 수 없음.*/
package com.geunbok.domain.posts;

import com.geunbok.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter //클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor //기본 생성자 자동 추가. public Posts() {}와 동일한 효과
@Entity
//테이블과 링크될 클래스임을 나타냄. 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭
//ex) SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity {
    @Id //Id: 해당 테이블의 PK 필드를 나타냄
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //GeneratedValue: PK의 생성 규칙을 나타냄
    private Long id;

    //Column: 굳이 선언하지 않아도 해당 클래스의 필드는 모두 칼럼임
    //선언하는 이유는 기본값 외에 추가로 변경이 필요한 옵션이 있을 때 사용
    //문자열의 경우 VARCHAR(255)가 기본값이지만 사이즈를 500으로(title) 늘리고 싶거나, 타입을 TEXT로(content) 변경하고 싶은 등의 경우에 사용
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder //해당 클래스의 빌더 패턴 클래스 생성. 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
