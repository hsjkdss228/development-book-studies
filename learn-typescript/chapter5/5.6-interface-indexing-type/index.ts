import StringArray from './StringArray';
import SalaryMap from './SalaryMap';
import User from './User';

var companies: StringArray = ['닌텐도', '펄어비스', '크래프톤'];

console.log(companies[0]);
console.log(companies[1]);

var salary: SalaryMap = {
  junior: 100,
  middle: 200,
  senior: 500,
}

console.log('junior\'s salary: ', salary.junior);
console.log('senior\'s salary: ', salary['senior']);

var inu: User = {
  id: 'hsjkdss228',
  name: '황인우',
  address: '광진구',
  skill: 'Supabase',
  1: '뭐'
}

console.log('이름: ', inu.name);
console.log('사용 가능한 기술 스택: ', inu.skill);
console.log(inu[1]);

var seungjun: User = {
  id: 'seungjun',
  name: '노승준',
}

console.log('이름: ', seungjun.name);
