%Sum of rectangular area
%for description how this works follow the link
%http://www.mathworks.se/help/toolbox/vision/ref/integralimage.html#

function A = ComputeBoxSum(ii_im, x, y, w, h)

ii_im=padarray(ii_im,[1 1], 0, 'pre');

[sR sC eR eC ] = deal(y , x ,   y+h-1, x+w-1);

A = ii_im(eR+1,eC+1) - ii_im(eR+1,sC) - ii_im(sR,eC+1) + ii_im(sR,sC);


end