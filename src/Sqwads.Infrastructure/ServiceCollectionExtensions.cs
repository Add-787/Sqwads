using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Sqwads.Infrastructure.Data;

namespace Sqwads.Infrastructure;

public static class ServiceCollectionExtensions
{
    public static IServiceCollection AddInfrastructure(this IServiceCollection services, IConfiguration configuration)
    {
        services.AddDbContext<ApplicationDbContext>(opts => opts.UseInMemoryDatabase(configuration.GetConnectionString("ApplicationDb")));
        return services;
    }

}
