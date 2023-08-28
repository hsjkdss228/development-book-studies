import Person from './Person';

// 에러 표출:
// 'Athlete' 인터페이스가 'Person' 인터페이스를 잘못 확장합니다.
// 'name' 속성의 형식이 호환되지 않습니다.
// 'number' 형식은 'string' 형식에 할당할 수 없습니다.

interface Athlete extends Person {
  name: number;
}

export default Athlete;
