function all_ftypes = EnumAllFeatures(W, H)
all_ftypes = zeros(35000,5);
f1 = [];
f2 = [];
f3 = [];
f4 = [];

%feature I
for w = 1:W-2
    for h = 1:floor(H/2)-2
        for x = 2:W-w
            for y = 2:H-2*h
                if x+w-1 <= W && y+h-1<= H
              f = [1,x,y,w,h];
              f1 = [f1;f];
                end
            end
        end
    end
end




%feature II
for w = 1:floor(W/2)-2
    for h = 1:H-2
        for x = 2:W-2*w
            for y = 2:H-h
                 if x+w-1 <= W && y+h-1<= H
              f = [2,x,y,w,h];
              f2 = [f2;f];   
                 end
            end
        end
    end
end



%feature III
for w = 1:floor(W/3)-2
    for h = 1:H-2
        for x = 2:W-3*w
            for y = 2:H-h
                 if x+w-1 <= W && y+h-1<= H
                 f = [3,x,y,w,h];
              f3 = [f3;f];
                 end
            end
        end
    end
end


%feature IV
for w = 1:floor(W/2)-2
    for h = 1:floor(H/2)-2
        for x = 2:W-2*w
            for y = 2:H-2*h
                 if x+w-1 <= W && y+h-1<= H
               f = [4,x,y,w,h];
              f4 = [f4;f];  
                 end
            end
        end
    end
end

all_ftypes = [f1;f2;f3;f4];

end