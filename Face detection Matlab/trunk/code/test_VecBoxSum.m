x = round(rand(1) * 18)
y = round(rand(1) * 18)
w = max(1, round(rand(1) * (19 - x)))
h = max(1, round(rand(1) * (19 - x)))
[im, ii_im] = LoadIm('TrainingImages/FACES/face00001.bmp');

b_vec   = VecBoxSum(x, y, w, h, 19, 19);
A1      = ii_im(:)' * b_vec;
A2      = ComputeBoxSum(ii_im, x, y, w, h);
eps     = 1e-5;
assert(abs(A1 - A2) < eps, 'VecBoxSum (%d) and ComputeBoxSum (%d) yield different results!', A1, A2);

fprintf('\nVecBoxSum test passed.\n');