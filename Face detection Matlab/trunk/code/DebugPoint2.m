dinfo2 = load('DebugInfo/debuginfo2.mat');
x = dinfo2.x; y = dinfo2.y; w = dinfo2.w; h = dinfo2.h;
abs(dinfo2.f1 - FeatureTypeI(ii_im, x, y, w, h)) > eps
abs(dinfo2.f2 - FeatureTypeII(ii_im, x, y, w, h)) > eps
abs(dinfo2.f3 - FeatureTypeIII(ii_im, x, y, w, h)) > eps
abs(dinfo2.f4 - FeatureTypeIV(ii_im, x, y, w, h)) > eps
