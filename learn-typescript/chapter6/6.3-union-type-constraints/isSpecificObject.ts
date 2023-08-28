import Person from './Person';
import Developer from './Developer';

function isPerson(someone: Person | Developer): someone is Person {
  return 'age' in someone;
}

function isDeveloper(someone: Person | Developer): someone is Developer {
  return 'skill' in someone;
}

var seungjun: Developer = {
  name: '노승준',
  skill: 'Spring'
};

console.log(isPerson(seungjun));
console.log(isDeveloper(seungjun));
