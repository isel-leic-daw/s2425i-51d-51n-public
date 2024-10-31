import { z } from 'zod';

const Student = z.object({
  name: z.string(),
  nbr: z.number(),
});

type Student = z.infer<typeof Student>;

export function demo() {
  const alice: Student = Student.parse({
    name: 'alice',
    nbr: 1234,
  });

  console.log(alice);
}
