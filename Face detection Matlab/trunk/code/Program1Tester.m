%Program 1 tester
dinfo1 = load('DebugInfo/debuginfo1.mat');
eps = 1e-6;
s1 = sum(abs(dinfo1.im(:) - im(:)) > eps)
s2 = sum(abs(dinfo1.ii_im(:) - ii_im(:)) > eps)