# jltk

jltk -- The Java Learning ToolKit

## What is jltk?

The jltk library is intended to be a single dependency that teachers and students can use in the early days of building
up their java skills.

As a teacher and curriculum-writer, teaching very junior folks -- with just a few months of python -- how to become
professional
java programmers, I have looked at a lot of code intended for that purpose, both starting projects and solution
projects.

I have become dissatisfied with two aspects of these offerings:

* Weak Solution Code: Almost uniformly, the solution projects contain code that I, personally, would not regard as of
  professional quality.
* Almost no attention to workflow, the actual practice of being a professional.

It isn't that the part the teacher is trying to _teach_ is so bad. Often, early in the learning, we're really aiming for
facility with a) a little OO syntax, and b) basic sequencing, branching, and looping to get to the target. The solution
code is often quite good at this.

It's the part(s) the students are learning from what is _not_ being taught.

If I am teaching developers to become (my style of) professionals, I don't _ever_ want them to see solution code or
solution workflow that doesn't match (my style of) professionalism.

### Addressing Weak Solution Code

Here are some of the things I see in solution code that I do not wish students to have as their earliest experiences of
being a professional java programmer:

* I/O deeply intermingled with domain logic,
    * usually based on `System.out` and `System.in`.
    * thus either untested or tested using inexplicable console-hacking.
* Uncaught exceptions,
    * `java.util.Scanner` is ubiquitous. It throws when it doesn't get what it wants, and most solution code ignores
      this.
    * Depending on the target problem, there will certainly be others: the JDK is rife with these complexities.
* Test-lessness. To the extent tests are present they:
    * Appear to be written after the fact.
    * Over-emphasize large-scale testing.

And the problem is that the skillset necessary to address these issues is far beyond the reach of folks who are in their
first two weeks in Java.

How to address it? Write a library that they can use, instead of and in addition to, the JDK that makes it possible for
the students to _safely_ roll their solutions w/o understanding exceptions or I/O-Domain separation.

### Addressing Workflow Detection & Monitoring

Further, I find myself frustrated that, in the _student's_ solution repos, we cannot show them their flow and teach from
it.

In (my style of) professional programming, we use very small steps, in development adding tests, in refactoring, keeping
those tests running, and when we make a safe change, we usually commit it on the spot.

I want to be able to not only see the workflow of the student, but to _share_ it with the students, showing them how it
works in my solution projects, and showing how it looks in their early efforts.

How to address it? Write a junit-extension that lets us do just that, enhanced with knowing a) when `main()` was run,
and b) when commits were done. Place this info, quietly and effortlessly, into the student's repo. Write a program to
parse and visualize this data in a way that will be helpful to us.

## What's Here Now?

### Round Zero







