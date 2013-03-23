x           = 3;
y           = 5;
w           = 2;
h           = 4;
eps         = 1e-5;
[im, ii_im] = LoadIm('TrainingImages/FACES/face00001.bmp');

% Test features type I.
ftype_vec   = VecFeature([1, x, y, w, h], 19, 19);
A1          = ii_im(:)' * ftype_vec;
A2          = FeatureTypeI(ii_im, x, y, w, h);
assert(abs(A1 - A2) < eps, 'VecFeature (%d) and FeatureTypeI (%d) yield different results!', A1, A2);

% Test features type II.
ftype_vec   = VecFeature([2, x, y, w, h], 19, 19);
A1          = ii_im(:)' * ftype_vec;
A2          = FeatureTypeII(ii_im, x, y, w, h);
assert(abs(A1 - A2) < eps, 'VecFeature (%d) and FeatureTypeII (%d) yield different results!', A1, A2);

% Test features type III.
ftype_vec   = VecFeature([3, x, y, w, h], 19, 19);
A1          = ii_im(:)' * ftype_vec;
A2          = FeatureTypeIII(ii_im, x, y, w, h);
assert(abs(A1 - A2) < eps, 'VecFeature (%d) and FeatureTypeIII (%d) yield different results!', A1, A2);

% Test features type IV.
ftype_vec   = VecFeature([4, x, y, w, h], 19, 19);
A1          = ii_im(:)' * ftype_vec;
A2          = FeatureTypeIV(ii_im, x, y, w, h);
assert(abs(A1 - A2) < eps, 'VecFeature (%d) and FeatureTypeIV (%d) yield different results!', A1, A2);


fprintf('\nVecFeature test passed.\n');
