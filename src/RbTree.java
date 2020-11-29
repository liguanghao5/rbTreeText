public class RbTree {

    public final boolean RED = false;  //红色代表false

    public final boolean BLACK = true; //黑色代表true


    private RbNode root;//根节点

    public RbNode getRoot() {
        return root;
    }

    public void setRoot(RbNode root) {
        this.root = root;
    }
    /**       n6                         n6
     *       /                           /③
     *       n1                         n2
     *     /   \                     ①/   \
     *   n5      n2       ---->      n1    n3
     *         /  \                 /  \②
     *       n4    n3              n5  n4
     * 左旋方法
     * @param n1
     */
    public void leftRotate(RbNode n1){
        if(n1!=null){
            RbNode n2 = n1.getRight();//右节点
            RbNode n6 = n1.getParent();//父节点
            RbNode n4 = n2.getLeft();    //右节点的左节点
            /**
             * 第一部分 ①
             */
            //设置n1的父节点为n2
            n1.setParent(n2);
            //设置n2的左节点为n1(节点关系双向设置)
            n2.setLeft(n1);
            /**
             * 第二部分 ②
             */
            //n1右节点为n4
            n1.setRight(n4);
            if(n4!=null){
                //n4父节点为n1
                n4.setParent(n1);
            }
            /**
             * 第三部分 ③
             */
            if(n6!=null){
                //如果n1是父节点的左节点
                if(n1==n6.getLeft()){
                    n6.setLeft(n2);
                 //n1是父节点的右节点
                }else{
                    n6.setRight(n2);
                }
            }else{
                //如果没有父节点的话 n2 成为了跟节点
                root = n2;
            }
            //n2的父节点是n6
            n2.setParent(n6);
        }
    }

    /**        n6                         n6
     *         /                          /③
     *        n1                        n2
     *      /   \                     /   \①
     *     n2    n5      ---->      n3    n1
     *    /  \                          ②/   \
     *  n3    n4                       n4     n5
     * 右旋旋方法
     * @param n1
     */
    public void rightRotate(RbNode n1){
        if(n1!=null){
            RbNode n2 = n1.getLeft();  //左节点
            RbNode n6 = n1.getParent(); //父节点
            RbNode n4 = n2.getRight();    //左节点的右节点
            /**
             * 第一部分 ①
             */
            //设置n1的父节点为n2
            n1.setParent(n2);
            //设置n2的右节点为n1(节点关系双向设置)
            n2.setRight(n1);
            /**
             * 第二部分 ②
             */
            //n1左节点为n4
            n1.setLeft(n4);
            if(n4!=null){
                //n4父节点为n1
                n4.setParent(n1);
            }
            /**
             * 第三部分 ③
             */
            if(n6!=null){
                //如果n1是父节点的左节点
                if(n1==n6.getLeft()){
                    n6.setLeft(n2);
                    //n1是父节点的右节点
                }else{
                    n6.setRight(n2);
                }

            }else{
                //如果没有父节点的话 n2 成为了跟节点
                root = n2;
            }
            //n2的父节点是n6,n6为空则设置为空
            n2.setParent(n6);
        }
    }

    /**
     * 添加节点
     * @param node
     */
    public void putNode(RbNode node){
        //①判断跟节点是否为空，如果为空则直接设置node为跟节点
        if(root==null){
            setColor(node,BLACK);
            root = node;

        }else{
            //②寻找落脚点
            RbNode x = root;//定义落脚节点
            RbNode p =null;//最后跳出循环时的父节点
            int i;//帮助记录是左节点还是右节点
           do{
                i = x.compareTo(node);
               //如果node小于x节点,比较x的左节点
               if(i>0){
                   p=x;
                   x=x.getLeft();
               //如果node大于x节点,比较x的右节点
               }else if(i<0){
                   p=x;
                   x=x.getRight();
               //如果node等于x节点，则直接覆盖x的值为新加的node的值
               }else{
                   x.setValue(node.getValue());
                   return;
               }
           }while (x!=null);//直到x节点为空则跳出循环
            if(i>0){//新节点比父节点小 在左边
                p.setLeft(node);

            }else if(i<0){//新节点比父节点大 在右边
                p.setRight(node);
            }
            node.setParent(p);

            //③调整位置
            fixAfterPut(node);

        }
    }

    /**
     * 这里分三种情况：
     * 第一种情况：父节点的颜色是黑色的，这种情况不用调整
     * 第二种情况：父节点的颜色为红色，并且父节点没有另一个红色子节点，这种情况需要调整
     * 第三种情况：父节点的颜色为红色，并且父节点有另一个红色子节点，这种情况也需要调整
     *
     * 那么针对上面的分析，对需要调整的两种情况进行继续分析
     * 情况二：
     *
     *
     *         一①                   一②                   一③                     一④
     *          n1黑                  n1黑                  n1红                      n3黑
     *        /    \                 /   \                 /   \                   /     \
     *     n2红     n4黑  --->   n3红   n4黑    --->      n3黑   n4黑   --->      n2红(x)   n1红
     *      \                   /                       /                                   \
     *     n3红(x)          n2红(x)                   n2红(x)                               n4黑
     * ps:情况二中如果n3是n2的右节点，则围绕n2先进行左旋操作
     *
     *     二①                         二②                    二③
     *     n1黑                         n1红                   n2黑
     *     /   \                       /  \                   /   \
     * n4黑    n2红     --->        n4黑   n2黑     --->     n1红    n3红(新)
     *          \                            \            /
     *        n3红(新)                        n3红(新)    n4黑
     * ps:情况二中如果n3是n2的左节点，则围绕n2先进行右旋操作
     * ==========================================================================
     * 情况三：
     *        n1黑                    n1红
     *       /   \                   /  \
     *     n2红    n3红     --->    n2黑  n3黑
     *             \                      \
     *             n4红(新)                n4红(新)
     * ps:情况三中如果n1为跟节点，则变红，如果n1的父节点为红，则继续围绕n1进行调整
     *
     *
     *
     * 调整树的位置，旋转，变色
     * @param x
     */
    private void fixAfterPut(RbNode x){

        //如果父节点为红色
        while (x!=null && x!=root && colorOf(parentOf(x))==RED){

            //父节点在爷爷节点的 "左边"
            if(parentOf(x)==leftOf(parentOf(parentOf(x)))){
                //叔叔节点
                RbNode ss = rightOf(parentOf(parentOf(x)));

                //叔叔节点是黑色的或者为空（情况二）
                if(colorOf(ss)==BLACK){

                    //如果x节点在父节点的右边(情况二种的ps情况)，由"一①"调整成"一②"或者"二①"
                    if(x==rightOf(parentOf(x))){
                        //图中的n2红顶替了n3红(新)的角色
                        x = parentOf(x);
                        //左旋父亲节点
                        leftRotate(x);
                    }
                    //父亲节点变黑
                    setColor(parentOf(x),BLACK);
                    //爷爷节点变红
                    setColor(parentOf(parentOf(x)),RED);

                    //右旋爷爷节点
                    rightRotate(parentOf(parentOf(x)));

                //叔叔节点是红色(情况三)
                }else{
                    //叔叔节点设置成黑色
                    setColor(ss,BLACK);
                    //父亲节点设置成黑色
                    setColor(parentOf(x),BLACK);
                    //爷爷节点设置成红色
                    setColor(parentOf(parentOf(x)),RED);
                    //因为可能爷爷的父节为红色，所以需要迭代爷爷节点进行调整
                    x = parentOf(parentOf(x));
                }
            //父节点在爷爷节点的 "右边"
            }else{
                //叔叔节点
                RbNode ss = leftOf(parentOf(parentOf(x)));

                //叔叔节点是黑色的或者为空（情况二）
                if (colorOf(ss) == BLACK) {

                    //如果x节点在父节点的左边(情况二种的ps情况)
                    if (x == leftOf(parentOf(x))) {

                        x = parentOf(x);
                        //右旋父亲节点
                        rightRotate(x);
                    }
                    //父亲节点变黑
                    setColor(parentOf(x), BLACK);
                    //爷爷节点变红
                    setColor(parentOf(parentOf(x)), RED);

                    //左旋爷爷节点
                    leftRotate(parentOf(parentOf(x)));

                //叔叔节点是红色(情况三)
                } else {
                    //叔叔节点设置成黑色
                    setColor(ss, BLACK);
                    //父亲节点设置成黑色
                    setColor(parentOf(x), BLACK);
                    //爷爷节点设置成红色
                    setColor(parentOf(parentOf(x)), RED);
                    //因为可能爷爷的父节为红色，所以需要迭代爷爷节点进行调整
                    x = parentOf(parentOf(x));
                }
            }
        }
        //最后跟节点是黑色的
        setColor(root,BLACK);
    }


    /**
     * 判断颜色是不是黑的
     * @param node
     * @return
     */
    private boolean colorOf(RbNode node) {
        //因为空节点默认为黑色
        return node == null ? BLACK : node.isColour();
    }

    /**
     * 获取父节点
     * @param node
     * @return
     */
    private RbNode parentOf(RbNode node) {
        return node != null ? node.getParent() : null;
    }

    /**
     * 获取左节点
     * @param node
     * @return
     */
    private RbNode leftOf(RbNode node) {
        return node != null ? node.getLeft() : null;
    }

    /**
     * 获取右节点
     * @param node
     * @return
     */
    private RbNode rightOf(RbNode node) {
        return node != null ? node.getRight() : null;
    }

    /**
     * 设置颜色
     * @param node
     * @param color
     */
    private void setColor(RbNode node, boolean color){
        if(node!=null){
            node.setColour(color);
        }
    }














}
