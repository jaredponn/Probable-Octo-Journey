# short script to to compare benchmarks
a = "./a"

b = "./b"

def printBenchMark(filename):
    with open(filename) as f:
        print(filename + " test")
        m = 0
        acc = 0
        for i in f:
            acc += float(i)
            m+=1
        print("average time: " + str(acc / m))
        print("total samples: " + str(m))


printBenchMark(a);
printBenchMark(b);
