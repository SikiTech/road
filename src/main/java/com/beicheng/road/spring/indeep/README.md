


{@code @Order} defines the sort order for an annotated component. 

Since Spring 4.0, annotation-based ordering is supported for many kinds of components in Spring, 
even for collection injection where the order values of the target components are taken into 
account (either from their target class or from their {@code @Bean} method).
 
 While such order 
values may influence priorities at injection points, please be aware that they do not influence 
singleton startup order which is an orthogonal concern determined by dependency relationships 
and {@code @DependsOn} declarations (influencing a runtime-determined dependency graph).