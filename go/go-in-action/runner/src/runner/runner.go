package runner

import (
    "errors"
    "os"
    "os/signal"
    "time"
)

// a Runner runs a set of tasks within a given timeout and can be shut down
// by an operating system interrupt

type Runner struct {

    // interrupt channel reports a signal from the OS
    interrupt chan os.Signal

    // complete channel that reports the processing is done
    complete chan error

    // timeout report that the time has run out
    timeout <-chan time.Time

    // tasks holds a set of functions that are executed synchronously
    // in index order

    tasks []func(int)
}

// ErrTimeout is returned when a value is received on the timeout channel
var ErrTimeout = errors.New("received timeout")

// ErrInterrupt is returned when an event from the OS is received
var ErrInterrupt = errors.New("received interrupt")

// New returns a new ready-to-use Runner
func New(d time.Duration) *Runner {

    return &Runner{
        interrupt: make(chan os.Signal, 1),
        complete: make(chan error),
        timeout: time.After(d),
    }
}

// Add attaches tasks to the Runner. A task is a function that takes an int ID
func (r *Runner) Add(tasks ...func(int)) {
    r.tasks = append(r.tasks, tasks ...)
}

// Start runs all tasks and monitors channel events
func (r *Runner) Start() error {

    // we want to reeive all interrupt based signals

    signal.Notify(r.interrupt, os.Interrupt)

    // Run all different tasks on different goroutine
    go func() {
        r.complete <- r.run()
    }()

    select {
    // Signaled when processing is done
    case err := <-r.complete:
        return err
    // Signaled when we run out of time
    case <-r.timeout:
        return ErrTimeout
    }
}

// run executes each registered task
func (r *Runner) run() error {

    for id, task := range r.tasks {

        // check for an interrupt signal from the OS
        if r.gotInterrupt() {
            return ErrInterrupt
        }

        // Execute the registered tasks
        task(id)
    }

    return nil
}

func (r *Runner) gotInterrupt() bool {
    select {
    // signaled when an interrupt event is sent
    case <-r.interrupt:
        // stop receiving any further signals
        signal.Stop(r.interrupt)
        return true
        // continue running as normal
        default:
            return false
    }
}