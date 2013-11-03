using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MathNet.Numerics.LinearAlgebra.Double;
using MathNet.Numerics.LinearAlgebra.Generic;

namespace Lab_1
{
    public class SimplexMethod
    {
        #region PROPERTIES

        public int m { get; set; }
        public int n { get; set; }

        public Matrix<double> A { get; set; }
        public Vector<double> b { get; set; }
        public Vector<double> c { get; set; }

        public Vector<double> plan { get; set; }  //начальные
        public Vector<double> basis { get; set; } //данные

        #endregion

        #region STEPS

        //Step0
        public Matrix<double> FindB()
        {
            var basis_columns = new List<Vector<double>>();
            foreach (var index in basis)
            {
                basis_columns.Add(A.Column((int)index));
            }
            var Ab = DenseMatrix.OfColumnVectors(basis_columns.ToArray());
            return Ab.Inverse();
        }

        public Dictionary<double, double> Step1(Matrix<double> B)
        {
            var not_basis_indexes_and_values = new Dictionary<double, double>();
            var c_basis = GetBasisVector(c, basis);
            var c_basis_row = c_basis.ToRowMatrix();
            var u_row = c_basis_row * B;
            var not_basis_indexes = GetNotBasisIndexes(basis);
            foreach (var index in not_basis_indexes)
            {
                var value = (u_row * A.Column((int)index))[0] - c[(int)index]; //оценка
                not_basis_indexes_and_values.Add(index, value);
            }
            return not_basis_indexes_and_values;
        }

        public bool Step2(Dictionary<double, double> not_basis_indexes_and_values)
        {
            bool planIsOptimal = true;
            foreach (var index_and_value in not_basis_indexes_and_values)
            {
                if (index_and_value.Value < 0)
                {
                    planIsOptimal = false;
                }
            }
            return planIsOptimal;
        }

        public Vector<double> Step3(Dictionary<double, double> not_basis_indexes_and_values, 
                                                                Matrix<double> B, out double j0)
        {
            j0 = -1;//выберем индекс по правилу Блэнда: j0 = min{j E Jn : value(j) < 0}
            foreach(var index_and_value in not_basis_indexes_and_values)
            {
                if (index_and_value.Value < 0)
                {
                    j0 = index_and_value.Key;
                    break;
                }
            }

            var z = B * A.Column((int)j0);
            foreach (var number in z)
            {
                if (number > 0)
                {
                    return z;
                }
            }
            throw new Exception("Задача не имеет решения в силу неограниченности" +
                    "сверху целевой функции на множестве планов.");
        }

        public KeyValuePair<double, double> Step4(Vector<double> z, out double s)
        {
            double minimal_theta = double.MaxValue;
            double js = -1;
            s = -1;
            var zi = 0;
            foreach(var i in basis)
            {
                if (z[zi] > 0)
                {
                    var theta = plan[(int)i] / z[zi];
                    if (theta < minimal_theta)
                    {
                        minimal_theta = theta;
                        js = i;
                        s = zi;
                    }
                }
                zi++;
            }
            return new KeyValuePair<double, double>(js, minimal_theta); //ДОЖНО БЫТЬ БОЛЬШЕ НУЛЯ ДЛЯ НЕВЫРОЖДЕННОСТИ, ПРОВЕРИТЬ
        }

        public void Step5(KeyValuePair<double, double> index_and_minimal_theta, double j0, Vector<double> z)
        {
            var new_plan = new DenseVector(plan.Count);
            new_plan[(int)j0] = index_and_minimal_theta.Value;
            int zi = 0;
            foreach (var index in basis)
            {
                new_plan[(int)index] = plan[(int)index] - index_and_minimal_theta.Value * z[zi];
                zi++;
            }
            plan = new_plan;

            var new_basis = new List<double>(basis.ToArray());
            int js = (int)index_and_minimal_theta.Key;
            new_basis.Remove(js);
            new_basis.Add(j0);
            new_basis.Sort();
            basis = DenseVector.OfEnumerable(new_basis);
        }

        public Matrix<double> Step6(Matrix<double> B, int s, Vector<double> z)
        {
            var M = DenseMatrix.Identity(m);
            var zs = (int)z[s];
            for (int i = 0; i < m; i++)
            {
                if (i == s)
                {
                    M[i, s] = 1.0 / zs;
                }
                else
                {
                    M[i, s] = -z[i] / zs;
                }
            }
            return M * B;
        }

        #endregion

        public void ExecuteSteps()
        {
            var B = FindB();
            while(true)
            {
                var not_basis_indexes_and_values = Step1(B);
                if (Step2(not_basis_indexes_and_values))
                {
                    Console.WriteLine("Plan is optimal.");
                    Console.WriteLine(plan.ToString());
                    return;
                }
                Console.WriteLine("Plan is not optimal.");
                Console.WriteLine(plan.ToString());

                double j0;
                var z = Step3(not_basis_indexes_and_values, B, out j0);
                double s;
                var index_and_minimal_theta = Step4(z, out s);
                Step5(index_and_minimal_theta, j0, z);
                B = Step6(B, (int)s, z);
            }
        }

        #region HELPING_FUNCTIONS

        private Vector<double> GetBasisVector(Vector<double> plan, Vector<double> basis)
        {
            var result = new List<double>();
            foreach (var index in basis)
            {
                result.Add(plan[(int)index]);
            }
            return DenseVector.OfEnumerable(result);
        }

        private Vector<double> GetNotBasisIndexes(Vector<double> basis)
        {
            List<double> all_indexes = new List<double>(n);
            for (int i = 0; i < n; i++)
            {
                all_indexes.Add(i);
            }
            foreach (var index in basis)
            {
                all_indexes.Remove((int)index);
            }
            return DenseVector.OfEnumerable(all_indexes);
        }

        #endregion
    }
}
