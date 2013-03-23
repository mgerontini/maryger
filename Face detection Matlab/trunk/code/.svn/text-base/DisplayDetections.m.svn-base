function DisplayDetections(im_name, dets)

im = imread(im_name);
figure
%load clown
%imagescm + min(im(:))) / (max(im(:)) + min(im(:)) );

image(im);

hold on;
axis equal

% Print rectangles
for i = 1:size(dets,1)
    rectangle('Position',[dets(i,1),dets(i,2)-1,dets(i,3),dets(i,4)-1], ...
    'LineWidth',1,'EdgeColor','red')
end

end