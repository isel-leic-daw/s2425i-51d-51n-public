/* eslint-disable */
/** @jsx createElement */
import { createElement } from './create-element';

type Student = {
  name: string;
  number: number;
};

const students: Array<Student> = [
  {
    name: 'Alice',
    number: 12345,
  },
  {
    name: 'Bob',
    number: 12346,
  },
  {
    name: 'Carol',
    number: 12347,
  },
];

export function renderStudents(students: Array<Student>): HTMLElement {
  return createElement(
    'table',
    { border: '1' },
    createElement('tr', {}, createElement('th', {}, 'Name'), createElement('th', {}, 'Number')),
    students.map(student =>
      createElement('tr', {}, createElement('td', {}, student.name), createElement('td', {}, student.number.toString()))
    )
  );
}

export function renderStudentsUsingTsx(students: Array<Student>): HTMLElement {
  return (
    <div>
      {students.map(student => (
        <div>
          <dl>
            <dt>Name</dt>
            <dd>{student.name}</dd>
            <dt>Number</dt>
            <dd>{student.number.toString()}</dd>
          </dl>
        </div>
      ))}
    </div>
  );
}

export function demo() {
  const parent = document.getElementById('main-div');
  parent.appendChild(renderStudents(students));
}
