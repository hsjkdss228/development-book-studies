import Hero from './Hero';
import Feature from './Feature';

function introduce(someone: Hero & Feature) {
  console.log('이름: ', someone.name);
  console.log('궁극기: ', someone.ultimate);
}

introduce({
  name: '라인하르트',
  ultimate: '대지 분쇄',
});

var nameProperty1: Hero = { name: '한조' };
var ultimateProperty1: Feature = { ultimate: '용의 분노' };

var hanjo: Hero & Feature = {
  ...nameProperty1,
  ...ultimateProperty1
};

introduce(hanjo);

// 다음의 경우에는 정상적으로 수행
interface Major {
  ultimate: string;
};

var nameProperty2: Hero = { name: '트레이서' };
var ultimateProperty2: Major = { ultimate: '펄스 폭탄' };

var tracer: Hero & Major = {
  ...nameProperty2,
  ...ultimateProperty2,
};

introduce(tracer);

// 다음을 실행할 경우 에러 발생
// introduce({ name: '겐지' });
// introduce({ ultimate: '펄스 폭탄' });

// 다음은 문법 오류를 검출
// introduce(nameProperty & ultimateProperty);

// 다음과 같이 object 타입의 객체를 펼침 연산자로 조합해 생성한 객체를 전달하는 경우에도 에러 발생
// var nameProperty3: object = { name: '리퍼' };
// var ultimateProperty3: object = { ultimate: '죽음의 꽃' };

// introduce({
//   ...nameProperty3,
//   ...ultimateProperty3,
// });
