# short script to to compare benchmarks
a = "./a"

def printBenchMark(filename):
    with open(filename) as f:
        print(filename + " test")
        m = 0
        acc = 0
        for i in f:
            tmp = i.split(': ')

            if tmp[0] != "BENCH":
                continue;

            try:
                acc += float(tmp[1])
                m+=1
            except ValueError:
                continue;

        print("average time in nanosecs: " + str(acc / m))
        print("average time in mlli: " + str(acc / m / 1000000))
        print("total samples: " + str(m))


printBenchMark(a);
