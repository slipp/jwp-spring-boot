INSERT INTO user (id, user_id, password, name, email) values (1, 'javajigi', 'test', '자바지기', 'javajigi@slipp.net');
INSERT INTO user (id, user_id, password, name, email) values (2, 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net');

INSERT INTO question (id, writer_id, title, contents, create_date, deleted) VALUES (1, 1, '국내에서 Ruby on Rails와 Play가 활성화되기 힘든 이유는 뭘까?', 'Ruby on Rails(이하 RoR)는 2006년 즈음에 정말 뜨겁게 달아올랐다가 금방 가라 앉았다. Play 프레임워크는 정말 한 순간 잠시 눈에 뜨이다가 사라져 버렸다. RoR과 Play 기반으로 개발을 해보면 정말 생산성이 높으며, 웹 프로그래밍이 재미있기까지 하다. Spring MVC + JPA(Hibernate) 기반으로 진행하면 설정할 부분도 많고, 기본으로 지원하지 않는 기능도 많아 RoR과 Play에서 기본적으로 지원하는 기능을 서비스하려면 추가적인 개발이 필요하다.', CURRENT_TIMESTAMP(), false);

INSERT INTO answer (writer_id, contents, create_date, question_id, deleted) VALUES (1, 'http://underscorejs.org/docs/underscore.html Underscore.js 강추합니다! 쓸일도 많고, 코드도 길지 않고, 자바스크립트의 언어나 기본 API를 보완하는 기능들이라 자바스크립트 이해에 도움이 됩니다. 무엇보다 라이브러리 자체가 아주 유용합니다.', CURRENT_TIMESTAMP(), 1, false);

INSERT INTO answer (writer_id, contents, create_date, question_id, deleted) VALUES (2, '언더스코어 강력 추천드려요. 다만 최신 버전을 공부하는 것보다는 0.10.0 버전부터 보는게 더 좋더군요. 코드의 변천사도 알 수 있고, 최적화되지 않은 코드들이 기능은 그대로 두고 최적화되어 가는 걸 보면 재미가 있습니다 :)', CURRENT_TIMESTAMP(), 1, false);

INSERT INTO question (id, writer_id, title, contents, create_date, deleted) VALUES (2, 2, 'runtime 에 reflect 발동 주체 객체가 뭔지 알 방법이 있을까요?', '설계를 희한하게 하는 바람에 꼬인 문제같긴 합니다만. 여쭙습니다. 상황은 mybatis select 실행될 시에 return object 의 getter 가 호출되면서인데요. getter 안에 다른 property 에 의존중인 코드가 삽입되어 있어서, 만약 다른 mybatis select 구문에 해당 property 가 없다면 exception 이 발생하게 됩니다.', CURRENT_TIMESTAMP(), false);