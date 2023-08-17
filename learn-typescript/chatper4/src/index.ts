interface Todo {
  id: number;
  title: string;
  done: boolean;
}

let todoItems: Todo[];

// api
function fetchTodoItems(): Todo[] {
  const todos: Todo[] = [
    {
      id: 1,
      title: '설거지하기',
      done: false,
    },
    {
      id: 2,
      title: '수영장 다녀오기',
      done: false,
    },
    {
      id: 3,
      title: '쉽시타 공부하기',
      done: false,
    },
  ];

  return todos;
}

// crud methods
function fetchTodos(): Todo[] {
  const todos: Todo[] = fetchTodoItems();
  return todos;
}

function addTodo(todo: Todo): void {
  todoItems = [...todoItems, todo];
}

function deleteTodo(index: number): void {
  // Array.splice(): https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array/splice
  todoItems.splice(index, 1);
}

function completeTodo(index: number): void {
  todoItems.splice(index, 1, { ...todoItems[index], done: true });
}

// business logic
// function logFirstTodo(): {
//   id: number;
//   title: string;
//   done: boolean;
//   // https://eslint.org/docs/latest/rules/indent -> indent ignoreNodes: FunctionDeclaration
//   // 함수 선언부에 대해 indent 검사를 수행하지 않음
//   // 함수의 반환 타입이 객체인 경우 객체 타입을 명시하는 과정에서 닫는 괄호를 줄바꿈으로 내렸을 때
//   // 닫는 괄호에 대해 indent 오류를 검출했는데, 이를 검사하지 않도록 지정
// } {
function logFirstTodo(): Todo {
  return todoItems[0];
}

function showCompleted(): Todo[] {
  // https://meetup.nhncloud.com/posts/243
  // rules: 'prettier/prettier': 'error'의 arrowParens를 'always'로 부여
  // 화살표 함수의 파라미터가 한 개인 경우에도 괄호로 감싸도록 함
  return todoItems.filter((item: Todo) => item.done);
}

function addTwoTodoItems(): void {
  addTodo({
    id: 4,
    title: '강호연파 다녀오기',
    done: false,
  });
  addTodo({
    id: 5,
    title: '게시판 AWS로 배포하기',
    done: false,
  });
}

// utils
function logTodoItems(): void {
  console.log(todoItems);
}

todoItems = fetchTodoItems();
addTwoTodoItems();
logTodoItems();
completeTodo(3);
console.log(showCompleted());
logTodoItems();
