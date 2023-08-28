import Person from './Person';

function logAge(someone: Person): void {
  console.log('age: ', someone.age);
}

var inu = { age: 29 };
logAge(inu);

var person = { name: 'seungjun', age: 25 };
logAge(person);
