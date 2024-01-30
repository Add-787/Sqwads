using Microsoft.Extensions.DependencyInjection;

namespace Sqwads.Application;

public static class ServiceCollectionsExtensions
{
    public static IServiceCollection AddApplication(this IServiceCollection serviceCollection)
    { 
        return serviceCollection;
    }
}
