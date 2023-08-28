import Person from './Person';
import Developer from './Developer';

function introduce(someone: Person | Developer) {
  console.log('이름: ', someone.name);

  // 다음을 직접 실행할 경우 에러 발생
  // console.log(someone.age);
  // console.log(someone.skill);

  if ('age' in someone) {
    console.log('나이: ', someone.age);
  }

  if ('skill' in someone) {
    console.log('기술 스택: ', someone.skill);
  }
}

introduce({
  name: '황인우',
  age: 29,
});

introduce({
  name: '노승준',
  skill: 'Spring',
});

function logText(text: string | number) {
  if (typeof text === 'string') {
    console.log(text.toUpperCase());
  }

  if (typeof text === 'number') {
    console.log(text.toLocaleString());
  }
}

logText('ababcdcdccc');
logText(1375);
