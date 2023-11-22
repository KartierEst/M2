/**
 * Implementation of a ring array (to be used as a queue)
 * author: chilowi@u-pem.fr
 * license: Apache 2
 */

package main

type RingArray[T any] struct {
    data []T
    start int
    length int
    maxCapacity int
}

func CreateRingArray[T any](initialCapacity int, maxCapacity int) *RingArray[T] {
    return &RingArray[T] {
        data: make([]T, initialCapacity),
        start: 0,
        length: 0,
        maxCapacity: maxCapacity}
}

func (r *RingArray[T]) Push(item T) {
    if r.length == len(r.data) && r.length < r.maxCapacity {
        // extend the array now
        capacity := len(r.data) * 2
        if capacity > r.maxCapacity {
            capacity = r.maxCapacity
        }
        newData := make([]T, capacity)
        for i := 0; i < r.length; i++ {
            newData[i] = r.data[(r.start + i) % len(r.data)]
        }
        r.data = newData
        r.start = 0
    }
    if r.length == len(r.data) {
        r.data[r.start] = item
        r.start = (r.start + 1) % len(r.data)
    } else {
        r.data[(r.start + r.length) % len(r.data)] = item
        r.length += 1
    }
}

func (r *RingArray[T]) Len() int {
    return r.length
}

func (r *RingArray[T]) Pop() T  {
    var result T
    if r.length > 0 {
        result = r.data[r.start]
        r.start = (r.start + 1) % len(r.data)
        r.length -= 1
    }
    return result
}

func (r *RingArray[T]) Get(i int) *T {
    var result *T
    if i < r.length {
        result = &(r.data[(r.start + i) % len(r.data)])
    }
    return result
}

func (r *RingArray[T]) Set(i int, item T) {
    if i < r.length {
        r.data[(r.start + i) % len(r.data)] = item
    }
}

