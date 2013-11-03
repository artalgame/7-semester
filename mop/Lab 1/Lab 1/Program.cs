using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MathNet.Numerics.LinearAlgebra.Double;

namespace Lab_1
{
    class Program
    {
        static void Main(string[] args)
        {
            var simplexMethod = new SimplexMethod();

            simplexMethod.m = 2;
            simplexMethod.n = 4;

            simplexMethod.A = DenseMatrix.OfArray(new double [,] {{3, 1, 1, 0}, 
                                                                  //{0, -2, 4, 1, 0, 0}, 
                                                                  //{0, 1, 0, 0, 1, 0}, 
                                                                  {1, -2, 0, 1}});
            simplexMethod.b = DenseVector.OfEnumerable(new double[] {1, 1});
            simplexMethod.c = DenseVector.OfEnumerable(new double[] {1, 4, 1, -1});
            simplexMethod.plan = DenseVector.OfEnumerable(new double[] {0, 0, 1, 1});
            simplexMethod.basis = DenseVector.OfEnumerable(new double[] {2, 3});

            simplexMethod.ExecuteSteps();
            Console.Read();
        }
    }
}
